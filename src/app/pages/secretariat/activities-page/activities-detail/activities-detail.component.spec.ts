import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivitiesDetailComponent } from './activities-detail.component';

describe('ActivitiesDetailComponent', () => {
  let component: ActivitiesDetailComponent;
  let fixture: ComponentFixture<ActivitiesDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ActivitiesDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivitiesDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
