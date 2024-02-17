import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoefficientSocialModalComponent } from './coefficient-social-modal.component';

describe('CoefficientSocialModalComponent', () => {
  let component: CoefficientSocialModalComponent;
  let fixture: ComponentFixture<CoefficientSocialModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CoefficientSocialModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CoefficientSocialModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
