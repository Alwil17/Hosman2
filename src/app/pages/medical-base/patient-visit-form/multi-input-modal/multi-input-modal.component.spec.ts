import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MultiInputModalComponent } from './multi-input-modal.component';

describe('MultiInputModalComponent', () => {
  let component: MultiInputModalComponent;
  let fixture: ComponentFixture<MultiInputModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MultiInputModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MultiInputModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
