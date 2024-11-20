import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarEmpresasComponent } from './listar-empresas.component';

describe('ListarEmpresasComponent', () => {
  let component: ListarEmpresasComponent;
  let fixture: ComponentFixture<ListarEmpresasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListarEmpresasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListarEmpresasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
