import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitInfosFormComponent } from './visit-infos-form.component';

describe('VisitInfosFormComponent', () => {
  let component: VisitInfosFormComponent;
  let fixture: ComponentFixture<VisitInfosFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VisitInfosFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VisitInfosFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
