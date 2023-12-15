import { MenuItem } from "./menu.model";

export const MENU: MenuItem[] = [
  {
    id: 1,
    label: "MENUITEMS.hosp.title",
    isTitle: true,
  },
  {
    id: 2,
    label: "MENUITEMS.hosp.new",
    link: "/hospitalisation/",
    icon: "ri-user-add-fill",
  },
]