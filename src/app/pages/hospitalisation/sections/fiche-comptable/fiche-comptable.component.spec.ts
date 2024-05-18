import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FicheComptableComponent } from './fiche-comptable.component';

describe('FicheComptableComponent', () => {
  let component: FicheComptableComponent;
  let fixture: ComponentFixture<FicheComptableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FicheComptableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FicheComptableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
