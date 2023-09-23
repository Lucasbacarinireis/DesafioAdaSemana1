import axios, { AxiosRequestConfig } from 'axios';

interface CustomAxiosRequestConfig extends AxiosRequestConfig {
  headers?: {
    Authorization?: string;
  };
}

const api = axios.create({
  baseURL: 'http://localhost:8080', 
  validateStatus: () => true,
});


api.interceptors.request.use(
  (config: CustomAxiosRequestConfig) => {
    config.headers = {
      ...config.headers,
      Authorization: `Bearer`,
    };


    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;