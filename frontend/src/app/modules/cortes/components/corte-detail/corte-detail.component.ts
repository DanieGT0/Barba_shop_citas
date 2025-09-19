import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';

import { CorteService } from '../../../../core/services/corte.service';
import { BarberoService } from '../../../../core/services/barbero.service';
import { Corte, TipoCorte } from '../../../../shared/models/corte.model';
import { Barbero } from '../../../../shared/models/barbero.model';

@Component({
  selector: 'app-corte-detail',
  templateUrl: './corte-detail.component.html',
  styleUrls: ['./corte-detail.component.scss']
})
export class CorteDetailComponent implements OnInit {
  corte: Corte | null = null;
  barberos: Barbero[] = [];
  loading = true;
  loadingBarberos = false;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private corteService: CorteService,
    private barberoService: BarberoService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const corteId = +params['id'];
      if (corteId) {
        this.loadCorte(corteId);
      }
    });
  }

  loadCorte(id: number): void {
    this.loading = true;
    this.error = null;

    this.corteService.getCorteById(id).subscribe({
      next: (corte) => {
        this.corte = corte;
        this.loading = false;
        this.loadBarberosEspecializados(id);
      },
      error: (error) => {
        this.error = 'Error al cargar el corte. Por favor, intenta de nuevo.';
        this.loading = false;
        console.error('Error loading corte:', error);
      }
    });
  }

  loadBarberosEspecializados(corteId: number): void {
    this.loadingBarberos = true;

    this.barberoService.getBarberosByEspecialidad(corteId).subscribe({
      next: (barberos) => {
        this.barberos = barberos;
        this.loadingBarberos = false;
      },
      error: (error) => {
        console.error('Error loading barberos:', error);
        this.loadingBarberos = false;
        // Load all available barbers as fallback
        this.barberoService.getBarberosDisponibles().subscribe({
          next: (barberos) => {
            this.barberos = barberos;
          },
          error: (fallbackError) => {
            console.error('Error loading fallback barberos:', fallbackError);
          }
        });
      }
    });
  }

  agendarCita(barbero?: Barbero): void {
    if (!this.corte) return;

    const queryParams: any = {
      corteId: this.corte.id
    };

    if (barbero) {
      queryParams.barberoId = barbero.id;
    }

    this.router.navigate(['/citas/agendar'], { queryParams });
  }

  goBack(): void {
    this.router.navigate(['/cortes']);
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

  shareCorte(): void {
    if (navigator.share) {
      navigator.share({
        title: this.corte?.nombre,
        text: this.corte?.descripcion,
        url: window.location.href
      });
    } else {
      // Fallback: copy to clipboard
      navigator.clipboard.writeText(window.location.href).then(() => {
        this.snackBar.open('Enlace copiado al portapapeles', 'Cerrar', {
          duration: 3000
        });
      });
    }
  }
}