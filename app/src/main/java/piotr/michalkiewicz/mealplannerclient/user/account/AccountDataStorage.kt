package piotr.michalkiewicz.mealplannerclient.user.account

import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount

final class AccountDataStorage {

    // TODO
    companion object{
        fun storeAccountData(accountData: UserAccount?){

        }
        fun getAccountData(): UserAccount{
            return  UserAccount.createMockUserAccount()
        }
    }
}