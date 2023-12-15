import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FicheAdresseComponent } from './fiche-adresse.component';

describe('FicheAdresseComponent', () => {
  let component: FicheAdresseComponent;
  let fixture: ComponentFixture<FicheAdresseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FicheAdresseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FicheAdresseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
