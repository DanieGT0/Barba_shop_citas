import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideAuth0 } from '@auth0/auth0-angular';
import { routes } from './app/app-routing.module';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    provideAnimationsAsync(),
    provideAuth0({
      domain: 'dev-q4jyr4x3f1gn43ed.us.auth0.com',
      clientId: 'yEfb4rV1fw1JrI90ptYJTIS3zBYK4w47',
      authorizationParams: {
        redirect_uri: window.location.origin + '/callback',
        audience: 'http://localhost:8080'
      }
    })
  ]
}).catch(err => console.error(err));