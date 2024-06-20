import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FamiliyHistoryComponent } from './familiy-history.component';

describe('FamiliyHistoryComponent', () => {
  let component: FamiliyHistoryComponent;
  let fixture: ComponentFixture<FamiliyHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FamiliyHistoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FamiliyHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
