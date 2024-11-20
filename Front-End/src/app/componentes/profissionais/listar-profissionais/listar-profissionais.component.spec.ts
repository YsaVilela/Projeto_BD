import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarProfissionaisComponent } from './listar-profissionais.component';

describe('ListarProfissionaisComponent', () => {
  let component: ListarProfissionaisComponent;
  let fixture: ComponentFixture<ListarProfissionaisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListarProfissionaisComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListarProfissionaisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
