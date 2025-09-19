export const environment = {
  production: true,
  apiUrl: 'https://your-backend-on-render.com/api',
  appName: 'BarberApp',
  oauth2: {
    google: {
      clientId: 'your-production-google-client-id',
      redirectUri: 'https://your-frontend-on-vercel.app/auth/callback'
    },
    github: {
      clientId: 'your-production-github-client-id',
      redirectUri: 'https://your-frontend-on-vercel.app/auth/callback'
    }
  }
};