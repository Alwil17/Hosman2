import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AtrdFormModalComponent } from './atrd-form-modal.component';

describe('AtrdFormModalComponent', () => {
  let component: AtrdFormModalComponent;
  let fixture: ComponentFixture<AtrdFormModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AtrdFormModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AtrdFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
