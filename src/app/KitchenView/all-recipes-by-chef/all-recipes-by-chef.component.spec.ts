import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllRecipesByChefComponent } from './all-recipes-by-chef.component';

describe('AllRecipesByChefComponent', () => {
  let component: AllRecipesByChefComponent;
  let fixture: ComponentFixture<AllRecipesByChefComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllRecipesByChefComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllRecipesByChefComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
