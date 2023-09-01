import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonToContactFormComponent } from './person-to-contact-form.component';

describe('PersonToContactFormComponent', () => {
  let component: PersonToContactFormComponent;
  let fixture: ComponentFixture<PersonToContactFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonToContactFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonToContactFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
