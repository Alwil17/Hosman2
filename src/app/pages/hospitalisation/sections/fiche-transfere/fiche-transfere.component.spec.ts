import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FicheTransfereComponent } from './fiche-transfere.component';

describe('FicheTransfereComponent', () => {
  let component: FicheTransfereComponent;
  let fixture: ComponentFixture<FicheTransfereComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FicheTransfereComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FicheTransfereComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
