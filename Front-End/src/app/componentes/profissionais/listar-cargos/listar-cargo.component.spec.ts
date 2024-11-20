import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarCargoComponent } from './listar-cargo.component';

describe('ListarCargoComponent', () => {
  let component: ListarCargoComponent;
  let fixture: ComponentFixture<ListarCargoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListarCargoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListarCargoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
