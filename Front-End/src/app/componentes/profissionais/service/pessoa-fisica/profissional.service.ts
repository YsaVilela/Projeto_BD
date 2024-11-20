import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Paginacao } from '../../interfaces/paginacao';
import { PessoaFisica } from '../../interfaces/pessoa-fisica';
import { Cidade } from '../../interfaces/cidade';

@Injectable({
  providedIn: 'root',
})
export class ProfissionalService {
  private readonly API = 'http://localhost:8080/pessoaFisica';
  constructor(private http: HttpClient) {}

  listar(): Observable<Paginacao> {
    return this.http.get<Paginacao>(`${this.API}/listar`);
  }

  cadastrar(profissional: PessoaFisica): Observable<PessoaFisica> {
    return this.http.post<PessoaFisica>(`${this.API}/cadastrar`, profissional);
  }

  buscarPorId(id: number | null): Observable<PessoaFisica> {
    return this.http.get<PessoaFisica>(`${this.API}/buscarId/${id}`);
  }

  buscarPorNome(nome: String | null): Observable<Paginacao> {
    return this.http.get<Paginacao>(`${this.API}/buscarNome/${nome}`);
  }

  buscarPorCpf(cpf: String | null): Observable<Paginacao> {
    return this.http.get<Paginacao>(`${this.API}/buscarCpf/${cpf}`);
  }

  buscarPorCargo(cargo: String | null): Observable<Paginacao> {
    return this.http.get<Paginacao>(`${this.API}/filtroCargo/${cargo}`);
  }

  atualizar(profissional: PessoaFisica): Observable<PessoaFisica> {
    return this.http.put<PessoaFisica>(`${this.API}/atualizar`, profissional);
  }

  atulizarStatus(id: number | undefined) {
    return this.http.put(`${this.API}/atualizarStatus/${id}`, null);
  }

  excluir(id: number | undefined) {
    return this.http.delete(`${this.API}/deletar/${id}`);
  }
}
