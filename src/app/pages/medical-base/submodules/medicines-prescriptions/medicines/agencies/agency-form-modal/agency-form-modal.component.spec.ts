import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgencyFormModalComponent } from './agency-form-modal.component';

describe('AgencyFormModalComponent', () => {
  let component: AgencyFormModalComponent;
  let fixture: ComponentFixture<AgencyFormModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgencyFormModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AgencyFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
