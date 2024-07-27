import { DOCUMENT } from '@angular/common';
import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CookieService } from 'ngx-cookie-service';
import { AuthenticationService } from 'src/app/core/services/auth.service';
import { AuthfakeauthenticationService } from 'src/app/core/services/authfake.service';
import { EventService } from 'src/app/core/services/event.service';
import { LanguageService } from 'src/app/core/services/language.service';
import { environment } from 'src/environments/environment';
import { MenuItem } from './menu.model';
import { MENU } from './menu';
import { CrStore } from '@stores/cr';

@Component({
  selector: 'app-cr',
  templateUrl: './cr.component.html',
  styleUrls: ['./cr.component.scss']
})
export class CrComponent implements OnInit {

  element: any;
  mode: string | undefined;
  @Output() mobileMenuButtonClicked = new EventEmitter();

  flagvalue: any;
  valueset: any;
  countryName: any;
  cookieValue: any;

  menu: any;
  toggle: any = true;
  menuItems: MenuItem[] = [];

  constructor(
    @Inject(DOCUMENT) private document: any,
    private eventService: EventService,
    public languageService: LanguageService,
    public _cookiesService: CookieService,
    public translate: TranslateService,
    private authService: AuthenticationService,
    private authFackservice: AuthfakeauthenticationService,
    private router: Router,
    private store: CrStore
  ) { 

    translate.setDefaultLang('fr');

  }

  ngOnInit(): void {

    this.menuItems = MENU;

    document.documentElement.setAttribute('data-layout', 'vertical');
    document.documentElement.setAttribute('data-topbar', 'light');
    document.documentElement.setAttribute('data-sidebar', 'dark');
    document.documentElement.setAttribute('data-sidebar-size', 'lg');
    document.documentElement.setAttribute('data-layout-style', 'default');
    document.documentElement.setAttribute('data-layout-mode', 'light');
    document.documentElement.setAttribute('data-layout-width', 'fluid');
    document.documentElement.setAttribute('data-layout-position', 'fixed');
    window.addEventListener('resize' , function(){
      if (window.screen.width <= 767) {
        document.documentElement.setAttribute('data-sidebar-size', '');
      }
      else if (window.screen.width <= 1024) {
        document.documentElement.setAttribute('data-sidebar-size', 'sm');
      }
      else if (window.screen.width >= 1024) {      
        document.documentElement.setAttribute('data-sidebar-size', 'lg');
      }
    })
    document.documentElement.setAttribute('data-layout-mode', 'light');
  }

   /***
   * Activate droup down set
   */
   ngAfterViewInit() {
    this.initActiveMenu();
  }

  removeActivation(items: any) {
    items.forEach((item: any) => {
      if (item.classList.contains("menu-link")) {
        if (!item.classList.contains("active")) {
          item.setAttribute("aria-expanded", false);
        }
        (item.nextElementSibling) ? item.nextElementSibling.classList.remove("show") : null;
      }
      if (item.classList.contains("nav-link")) {
        if (item.nextElementSibling) {
          item.nextElementSibling.classList.remove("show");
        }
        item.setAttribute("aria-expanded", false);
      }
      item.classList.remove("active");
    });
  }


  /**
   * Topbar Light-Dark Mode Change
   */
  changeMode() {
    var html = document.getElementsByTagName("HTML")[0];
    var mode = html.hasAttribute("data-layout-mode") && html.getAttribute("data-layout-mode") == "dark" ? 'light' : 'dark'
    html.setAttribute("data-layout-mode", mode);

    this.mode = mode;
    this.eventService.broadcast('changeMode', mode);

    switch (mode) {
      case 'light':
        document.body.setAttribute('data-layout-mode', "light");
        document.body.setAttribute('data-sidebar', "light");
        localStorage.setItem("data-layout-mode", "light");
        break;
      case 'dark':
        document.body.setAttribute('data-layout-mode', "dark");
        document.body.setAttribute('data-sidebar', "dark");
        localStorage.setItem("data-layout-mode", "dark");
        break;
      default:
        document.body.setAttribute('data-layout-mode', "light");
        localStorage.setItem("data-layout-mode", "light");
        break;
    }
  }

  goToAppsList() {
    this.router.navigateByUrl("apps");
  }

    /**
   * Logout the user
   */
    logout() {
      if (environment.defaultauth === "firebase") {
        this.authService.logout();
      } else {
        this.authFackservice.logout();
      }
      this.router.navigate(["/auth/login"]);
    }

    toggleSubItem(event: any) {
      let isCurrentMenuId = event.target.closest('a.nav-link');
      let isMenu = isCurrentMenuId.nextElementSibling as any;
      let dropDowns = Array.from(document.querySelectorAll('.sub-menu'));
      dropDowns.forEach((node: any) => {
        node.classList.remove('show');
      });
  
      let subDropDowns = Array.from(document.querySelectorAll('.menu-dropdown .nav-link'));
      subDropDowns.forEach((submenu: any) => {
        submenu.setAttribute('aria-expanded',"false");
      });
      
      if (event.target && event.target.nextElementSibling){
        isCurrentMenuId.setAttribute("aria-expanded", "true");
        event.target.nextElementSibling.classList.toggle("show");
      }
    };
  
    toggleExtraSubItem(event: any) {
      let isCurrentMenuId = event.target.closest('a.nav-link');
      let isMenu = isCurrentMenuId.nextElementSibling as any;
      let dropDowns = Array.from(document.querySelectorAll('.extra-sub-menu'));
      dropDowns.forEach((node: any) => {
        node.classList.remove('show');
      });
  
      let subDropDowns = Array.from(document.querySelectorAll('.menu-dropdown .nav-link'));
      subDropDowns.forEach((submenu: any) => {
        submenu.setAttribute('aria-expanded',"false");
      });
      
      if (event.target && event.target.nextElementSibling){
        isCurrentMenuId.setAttribute("aria-expanded", "true");
        event.target.nextElementSibling.classList.toggle("show");
      }
    };
  
    toggleItem(event: any) {
      let isCurrentMenuId = event.target.closest('a.nav-link');
      let isMenu = isCurrentMenuId.nextElementSibling as any;
      if (isMenu.classList.contains("show")) {
        isMenu.classList.remove("show");
        isCurrentMenuId.setAttribute("aria-expanded", "false");
      } else {
        let dropDowns = Array.from(document.querySelectorAll('#navbar-nav .show'));
        dropDowns.forEach((node: any) => {
          node.classList.remove('show');
        });
        (isMenu) ? isMenu.classList.add('show') : null;
        const ul = document.getElementById("navbar-nav");
        if (ul) {
          const iconItems = Array.from(ul.getElementsByTagName("a"));
          let activeIconItems = iconItems.filter((x: any) => x.classList.contains("active"));
          activeIconItems.forEach((item: any) => {
            item.setAttribute('aria-expanded', "false")
            item.classList.remove("active");
          });
        }
        isCurrentMenuId.setAttribute("aria-expanded", "true");
        if (isCurrentMenuId) {
          this.activateParentDropdown(isCurrentMenuId);
        }
      }
    }
  
    // remove active items of two-column-menu
    activateParentDropdown(item:any) {
      item.classList.add("active");
      let parentCollapseDiv = item.closest(".collapse.menu-dropdown");
  
      if (parentCollapseDiv) {
          // to set aria expand true remaining
          parentCollapseDiv.classList.add("show");
          parentCollapseDiv.parentElement.children[0].classList.add("active");
          parentCollapseDiv.parentElement.children[0].setAttribute("aria-expanded", "true");
          if (parentCollapseDiv.parentElement.closest(".collapse.menu-dropdown")) {
              parentCollapseDiv.parentElement.closest(".collapse").classList.add("show");
              if (parentCollapseDiv.parentElement.closest(".collapse").previousElementSibling)
                  parentCollapseDiv.parentElement.closest(".collapse").previousElementSibling.classList.add("active");
              if (parentCollapseDiv.parentElement.closest(".collapse").previousElementSibling.closest(".collapse")) {
                  parentCollapseDiv.parentElement.closest(".collapse").previousElementSibling.closest(".collapse").classList.add("show");
                  parentCollapseDiv.parentElement.closest(".collapse").previousElementSibling.closest(".collapse").previousElementSibling.classList.add("active");
              }
          }
          return false;
      }
      return false;
    }
  

    updateActive(event: any) {
      const ul = document.getElementById("navbar-nav");
      if (ul) {
        const items = Array.from(ul.querySelectorAll("a.nav-link"));
        this.removeActivation(items);
      }
      this.activateParentDropdown(event.target);
    }
  
    initActiveMenu() {
      const pathName = window.location.pathname;
      const ul = document.getElementById("navbar-nav");
      if (ul) {
        const items = Array.from(ul.querySelectorAll("a.nav-link"));
        let activeItems = items.filter((x: any) => x.classList.contains("active"));
        this.removeActivation(activeItems);
  
        let matchingMenuItem = items.find((x: any) => {
          return x.pathname === pathName;
        });
        if (matchingMenuItem) {
          this.activateParentDropdown(matchingMenuItem);
        }
      }
    } 


      /**
   * Returns true or false if given menu item has child or not
   * @param item menuItem
   */
  hasItems(item: MenuItem) {
    return item.subItems !== undefined ? item.subItems.length > 0 : false;
  }

  /**
   * Toggle the menu bar when having mobile screen
   */
  toggleMobileMenu(event: any) {
    var sidebarsize = document.documentElement.getAttribute("data-sidebar-size");
    if (sidebarsize == 'sm-hover-active') {
      document.documentElement.setAttribute("data-sidebar-size", 'sm-hover')
    } else {
      document.documentElement.setAttribute("data-sidebar-size", 'sm-hover-active')
    }
  }

  /**
   * SidebarHide modal
   * @param content modal content
   */
   SidebarHide() {
    document.body.classList.remove('vertical-sidebar-enable');
  }
  
  

}
