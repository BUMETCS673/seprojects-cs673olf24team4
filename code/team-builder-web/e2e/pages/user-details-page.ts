import { expect, Locator, Page } from "@playwright/test";

export class UserDetailsPage {
    readonly page: Page;
    readonly addUserLink: Locator;
    readonly nameInput: Locator;
    readonly emailInput: Locator;
    readonly skillsInput: Locator;
    readonly preferredRoleInput: Locator;
    readonly saveButton: Locator;

    constructor(page: Page) {
        this.page = page;
        this.addUserLink = page.locator('a', { hasText: 'Add User' });
        this.nameInput = page.getByLabel('Name');
        this.emailInput = page.getByLabel('Email');
        this.skillsInput = page.getByLabel('Programming Languages & Frameworks');
        this.preferredRoleInput = page.getByLabel('Preferred Role');
        this.saveButton = page.getByRole('button', {name: 'Save'});
    }

    async goto() {
        await this.page.goto('http://localhost:4200/');
    }

    async navigateToAddUserPage() {
        await this.addUserLink.click();
    }

    async expectSaveButtonToBeDisabled() {
        await expect(this.saveButton).toBeDisabled();
    }

    async addUserDetails(name: string, email: string, skills: string[], preferredRole: string) {
        await this.nameInput.fill(name);
        await this.emailInput.fill(email);
        for (const skill of skills) {
            await this.skillsInput.first().fill(skill);
            await this.skillsInput.first().press('Enter')
        }
        await this.preferredRoleInput.click();
        await this.page.getByRole('option', {name: preferredRole}).click();
    }

    async expectSaveButtonToBeEnabled() {
        await expect(this.saveButton).toBeEnabled();
    }
    
}