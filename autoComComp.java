public class autoComComp{
    
    public boolean accountDetailVisible{get; set;}
    
    public string searchTerm{get; set;}
    public string selectedAccountId{get; set;}
    public list<Account> selectedAccount{get; set;}
    
    
    @RemoteAction  
    public static List<AccountWrapper> getSearchSuggestions(String searchString){  
       List<AccountWrapper> accountWrappers = new List<AccountWrapper>();  
       List<List<sObject>> searchObjects = [FIND :searchString + '*' IN ALL FIELDS RETURNING Account (Id, Name)];  
       if(!searchObjects.isEmpty()){  
            for(List<SObject> objects : searchObjects){  
                 for(SObject obj : objects){
                      if(obj.getSObjectType().getDescribe().getName().equals('Account')){  
                           Account acct = (Account)obj;  
                           accountWrappers.add(new AccountWrapper(acct.name, acct.Id));  
                      } 
                 }  
            }  
       }  
       system.debug('accountWrappers++'+accountWrappers);
       return accountWrappers;  
    }
    
    public class AccountWrapper {  
        public String label { get; set; }  
        public String value { get; set; }  
        public AccountWrapper (String label, String value){  
           this.label = label;  
           this.value = value;  
        }  
    }
    
    
    public void showAccountDetail(){
      selectedAccount = [Select Id,Name,Website,AccountNumber,Phone ,Fax FROM Account WHERE Id = :selectedAccountId LIMIT 1];
      accountDetailVisible= true;
    }

    
}