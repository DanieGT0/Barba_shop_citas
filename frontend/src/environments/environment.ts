export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
  appName: 'BarberApp',
  oauth2: {
    google: {
      clientId: 'your-google-client-id',
      redirectUri: 'http://localhost:4200/auth/callback'
    },
    github: {
      clientId: 'your-github-client-id',
      redirectUri: 'http://localhost:4200/auth/callback'
    }
  }
};