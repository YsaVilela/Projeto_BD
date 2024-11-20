import { Cargo } from "./cargo";
import { Pessoa } from "./pessoa";

export interface PessoaFisica{
  id?: number;
  pessoa: Pessoa;
  cpf: string;
  cargo: Cargo;
}

