import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeceasedFormModalComponent } from './deceased-form-modal.component';

describe('DeceasedFormModalComponent', () => {
  let component: DeceasedFormModalComponent;
  let fixture: ComponentFixture<DeceasedFormModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeceasedFormModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeceasedFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
