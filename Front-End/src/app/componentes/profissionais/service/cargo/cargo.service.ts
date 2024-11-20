import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cargo } from '../../interfaces/cargo';
import { Observable } from 'rxjs';
import { Paginacao } from '../../interfaces/paginacao';

@Injectable({
  providedIn: 'root'
})
export class CargoService {
  private readonly API = 'http://localhost:8080/cargo'
  constructor(private http: HttpClient) {}

  listar(): Observable<Paginacao> {
    return this.http.get<Paginacao>(`${this.API}/listar`);
  }

  cadastrar(cargo: Cargo): Observable<Cargo>{
    return this.http.post<Cargo>(`${this.API}/cadastrar`, cargo);
  }

  buscarPorNome(nomeCargo: String): Observable<Cargo>{
    return this.http.get<Cargo>(`${this.API}/buscarNome/${nomeCargo}`);
  }

  buscarPorNomeDinamico(nomeCargo: String): Observable<Paginacao>{
    return this.http.get<Paginacao>(`${this.API}/buscarNomeDinamico/${nomeCargo}`);
  }

  buscarPorId(id: number): Observable<Cargo>{
    return this.http.get<Cargo>(`${this.API}/buscarId/${id}`);
  }

  atualizar(cargo: Cargo): Observable<Cargo> {
    return this.http.put<Cargo>(`${this.API}/atualizar`, cargo);
  }

  excluir(id: number | undefined){
    return this.http.delete(
      `${this.API}/deletar/${id}`
    );
  }
}
