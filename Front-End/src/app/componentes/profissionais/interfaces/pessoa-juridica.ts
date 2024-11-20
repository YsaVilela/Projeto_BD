import { Pessoa } from "./pessoa";

export interface PessoaJuridica{
  id?: number;
  pessoa: Pessoa;
  cnpj: string;
}
