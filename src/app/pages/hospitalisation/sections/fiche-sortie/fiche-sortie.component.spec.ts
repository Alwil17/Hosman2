import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FicheSortieComponent } from './fiche-sortie.component';

describe('FicheSortieComponent', () => {
  let component: FicheSortieComponent;
  let fixture: ComponentFixture<FicheSortieComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FicheSortieComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FicheSortieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
