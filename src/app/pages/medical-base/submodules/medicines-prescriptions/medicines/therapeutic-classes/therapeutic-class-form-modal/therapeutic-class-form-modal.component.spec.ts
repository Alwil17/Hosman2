import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TherapeuticClassFormModalComponent } from './therapeutic-class-form-modal.component';

describe('TherapeuticClassFormModalComponent', () => {
  let component: TherapeuticClassFormModalComponent;
  let fixture: ComponentFixture<TherapeuticClassFormModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TherapeuticClassFormModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TherapeuticClassFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
