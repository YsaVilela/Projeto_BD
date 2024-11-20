import { Endereco } from "./endereco";

export interface Pessoa {
  id?: number;
  nome: string;
  dataDeConstituicao: string;
  email: string;
  telefone: string;
  endereco: Endereco;
  status?: boolean;
}
