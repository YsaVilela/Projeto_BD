import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfissionalComponent } from './profissional.component';

describe('ProfissionalComponent', () => {
  let component: ProfissionalComponent;
  let fixture: ComponentFixture<ProfissionalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfissionalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfissionalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
