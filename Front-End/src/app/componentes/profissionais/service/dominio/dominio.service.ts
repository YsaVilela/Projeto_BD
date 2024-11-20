import { Endereco } from './../../interfaces/endereco';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cidade } from '../../interfaces/cidade';
import { Paginacao } from '../../interfaces/paginacao';

@Injectable({
  providedIn: 'root',
})
export class DominioService {
  private readonly API = 'http://localhost:8080';
  constructor(private http: HttpClient) {}

  buscarCidade(endereco: Endereco): Observable<Cidade> {
    const siglaEstado: String = endereco.cidade.estado.uf;
    const nomeCidade: String = endereco.cidade.nome
    return this.http.get<Cidade>(`${this.API}/cidades/${siglaEstado}/${nomeCidade}`);
  }

  buscarCidadePorEstado(siglaEstado: String): Observable<Paginacao> {
    return this.http.get<Paginacao>(`${this.API}/cidades/buscarCidadePorEstado/${siglaEstado}`);
  }

  listarEstados(): Observable<Paginacao> {
    return this.http.get<Paginacao>(`${this.API}/estados`);
  }
}
