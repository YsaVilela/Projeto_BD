import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarCargoComponent } from './criar-cargo.component';

describe('CriarCargoComponent', () => {
  let component: CriarCargoComponent;
  let fixture: ComponentFixture<CriarCargoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CriarCargoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CriarCargoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
