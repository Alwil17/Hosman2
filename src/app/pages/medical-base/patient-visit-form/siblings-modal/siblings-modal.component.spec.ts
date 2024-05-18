import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SiblingsModalComponent } from './siblings-modal.component';

describe('SiblingsModalComponent', () => {
  let component: SiblingsModalComponent;
  let fixture: ComponentFixture<SiblingsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SiblingsModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SiblingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
