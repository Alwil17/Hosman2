import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SiblingsNumberModalComponent } from './siblings-number-modal.component';

describe('SiblingsNumberModalComponent', () => {
  let component: SiblingsNumberModalComponent;
  let fixture: ComponentFixture<SiblingsNumberModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SiblingsNumberModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SiblingsNumberModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
