import { test } from '@playwright/test';
import { UserDetailsPage } from '../pages/user-details-page';

test('should fill user details and enable save button', async ({ page }) => {
  const userDetailsPage = new UserDetailsPage(page);
  await userDetailsPage.goto();
  await userDetailsPage.navigateToAddUserPage();
  await userDetailsPage.expectSaveButtonToBeDisabled();
  await userDetailsPage.addUserDetails('Test User', 'test.user@tb.com', ['Python', 'Flask'], 'Team Leader'); 
  await userDetailsPage.expectSaveButtonToBeEnabled();
  await userDetailsPage.saveUser();
  await userDetailsPage.expectSuccessNotification();
});
