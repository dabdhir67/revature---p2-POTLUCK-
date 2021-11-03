import { Chef } from "./Chef";

export class Recipe {

    title:string;
    body:string;
    date:Date;
    chef:Chef;


    constructor(title:string, body:string, date:Date, chef:Chef){
        this.title=title;
        this.body=body;
        this.date=date;
        this.chef=chef;
    }
}
