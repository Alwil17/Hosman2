interface Option {
    id: number;
    text?: string;
}

interface Condition {
    eval: string;
    text: string;
}

interface Error {
    validator: string; // evaluation string
    message: string; // errormessage
}

interface Field {
    name?: string;
    label?: string;
    subtext?: string;
    units?: string;
    type?: string;
    component?: string;
    col: number;
    default?: any;
    show?: boolean;
    padding?: boolean;
    options?: Option[];
    if?: { name: string; value: any }[]; // condition d'affichage
    conditions?: Condition[]; // condition de wording
    bindlabel?: string; // si input select , label name in dropdown
    errors?: Error[] // validation avec message
}

export interface Section {
    name?:string;
    groupName?: string;
    template: Field[];
    resume: string;
}