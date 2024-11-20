import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PessoaJuridica } from '../../interfaces/pessoa-juridica';
import { Observable } from 'rxjs';
import { Paginacao } from '../../interfaces/paginacao';

@Injectable({
  providedIn: 'root',
})
export class EmpresaService {
  private readonly API = 'http://localhost:8080/pessoaJuridica';
  constructor(private http: HttpClient) {}

  listar(): Observable<Paginacao> {
    return this.http.get<Paginacao>(`${this.API}/listar`);
  }

  cadastrar(empresa: PessoaJuridica): Observable<PessoaJuridica> {
    return this.http.post<PessoaJuridica>(
      `${this.API}/cadastrar`,
      empresa
    );
  }

  buscarPorId(id: number | null): Observable<PessoaJuridica> {
    return this.http.get<PessoaJuridica>(`${this.API}/buscarId/${id}`);
  }

  buscarPorNome(nome: String | null): Observable<Paginacao> {
    return this.http.get<Paginacao>(`${this.API}/buscarNome/${nome}`);
  }

  atualizar(empresa: PessoaJuridica): Observable<PessoaJuridica> {
    return this.http.put<PessoaJuridica>(`${this.API}/atualizar`, empresa);
  }

  atulizarStatus(id: number | undefined) {
    return this.http.put(`${this.API}/atualizarStatus/${id}`, null);
  }

  excluir(id: number | undefined){
    return this.http.delete(
      `${this.API}/deletar/${id}`
    );
  }
}
