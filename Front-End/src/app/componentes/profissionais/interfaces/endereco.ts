import { Cidade } from "./cidade";

export interface Endereco {
  id?: number;
  cep: string;
  logradouro: string;
  numero: number;
  complemento: string;
  cidade: Cidade;
}
