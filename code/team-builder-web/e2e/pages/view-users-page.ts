import { expect, Locator, Page } from "@playwright/test";

export class ViewUsersPage {
    readonly page: Page;
    readonly viewUsersLink: Locator;

    constructor(page: Page) {
        this.page = page;
        this.viewUsersLink = page.locator('a', { hasText: 'View Users' });
    }

    async goto() {
        await this.page.goto('http://localhost:4200/view-users');
    }
    
    async navigateToViewUsersPage() {
        await this.viewUsersLink.click();
    }

    async expectUserToBePresent(name: string, email: string) {
        // TODO
    }

}