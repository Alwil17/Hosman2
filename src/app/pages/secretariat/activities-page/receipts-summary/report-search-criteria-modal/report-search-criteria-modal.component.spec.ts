import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportSearchCriteriaModalComponent } from './report-search-criteria-modal.component';

describe('ReportSearchCriteriaModalComponent', () => {
  let component: ReportSearchCriteriaModalComponent;
  let fixture: ComponentFixture<ReportSearchCriteriaModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportSearchCriteriaModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportSearchCriteriaModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
