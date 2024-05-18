import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FicheTransfuseComponent } from './fiche-transfuse.component';

describe('FicheTransfuseComponent', () => {
  let component: FicheTransfuseComponent;
  let fixture: ComponentFixture<FicheTransfuseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FicheTransfuseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FicheTransfuseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
