import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecialRequestsPageComponent } from './special-requests-page.component';

describe('SpecialRequestsPageComponent', () => {
  let component: SpecialRequestsPageComponent;
  let fixture: ComponentFixture<SpecialRequestsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpecialRequestsPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SpecialRequestsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
