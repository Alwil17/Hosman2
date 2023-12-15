import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedecinExterneComponent } from './medecin-externe.component';

describe('MedecinExterneComponent', () => {
  let component: MedecinExterneComponent;
  let fixture: ComponentFixture<MedecinExterneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedecinExterneComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MedecinExterneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
