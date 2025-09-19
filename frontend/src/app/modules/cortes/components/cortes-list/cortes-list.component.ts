import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable, BehaviorSubject, combineLatest } from 'rxjs';
import { map, startWith, debounceTime, distinctUntilChanged } from 'rxjs/operators';

import { CorteService } from '../../../../core/services/corte.service';
import { Corte, TipoCorte } from '../../../../shared/models/corte.model';

@Component({
  selector: 'app-cortes-list',
  templateUrl: './cortes-list.component.html',
  styleUrls: ['./cortes-list.component.scss']
})
export class CortesListComponent implements OnInit {
  cortes$ = new BehaviorSubject<Corte[]>([]);
  filteredCortes$: Observable<Corte[]>;
  loading = true;
  error: string | null = null;

  filterForm: FormGroup;
  tiposCorte = Object.values(TipoCorte);

  constructor(
    private corteService: CorteService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.filterForm = this.fb.group({
      searchTerm: [''],
      tipo: [''],
      precioMin: [null],
      precioMax: [null]
    });

    this.filteredCortes$ = combineLatest([
      this.cortes$.asObservable(),
      this.filterForm.valueChanges.pipe(
        startWith(this.filterForm.value),
        debounceTime(300),
        distinctUntilChanged()
      )
    ]).pipe(
      map(([cortes, filters]) => this.applyFilters(cortes, filters))
    );
  }

  ngOnInit(): void {
    this.loadCortes();
  }

  loadCortes(): void {
    this.loading = true;
    this.error = null;

    this.corteService.getAllCortes().subscribe({
      next: (cortes) => {
        this.cortes$.next(cortes);
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Error al cargar los cortes. Por favor, intenta de nuevo.';
        this.loading = false;
        console.error('Error loading cortes:', error);
      }
    });
  }

  private applyFilters(cortes: Corte[], filters: any): Corte[] {
    return cortes.filter(corte => {
      const matchesSearch = !filters.searchTerm ||
        corte.nombre.toLowerCase().includes(filters.searchTerm.toLowerCase()) ||
        (corte.descripcion && corte.descripcion.toLowerCase().includes(filters.searchTerm.toLowerCase()));

      const matchesTipo = !filters.tipo || corte.tipo === filters.tipo;

      const matchesPrecioMin = !filters.precioMin ||
        (corte.precioEstimado && corte.precioEstimado >= filters.precioMin);

      const matchesPrecioMax = !filters.precioMax ||
        (corte.precioEstimado && corte.precioEstimado <= filters.precioMax);

      return matchesSearch && matchesTipo && matchesPrecioMin && matchesPrecioMax;
    });
  }

  viewCorteDetail(corte: Corte): void {
    this.router.navigate(['/cortes', corte.id]);
  }

  clearFilters(): void {
    this.filterForm.reset();
  }

  getTipoDisplayName(tipo: TipoCorte): string {
    const displayNames: Record<TipoCorte, string> = {
      [TipoCorte.CLASICO]: 'Clásico',
      [TipoCorte.MODERNO]: 'Moderno',
      [TipoCorte.BARBA]: 'Barba',
      [TipoCorte.BIGOTE]: 'Bigote',
      [TipoCorte.COMPLETO]: 'Completo'
    };
    return displayNames[tipo] || tipo;
  }
}