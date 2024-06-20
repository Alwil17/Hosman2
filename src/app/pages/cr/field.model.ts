interface Option {
    id: number;
    text?: string;
}

interface Condition {
    eval: string;
    text: string;
}

interface Field {
    name?: string;
    label?: string;
    subtext?: string;
    type?: string;
    component?: string;
    col: number;
    default?: any;
    show?: boolean;
    padding?: boolean;
    options?: Option[];
    if?: { name: string; value: any }[];
    conditions?: Condition[];
    bindlabel?: string;
    format?: string;
}

export interface Section {
    template: Field[];
    resume: string;
}