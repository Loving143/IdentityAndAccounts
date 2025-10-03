# IdentityAndAccounts
//Here I have put some terminilogy regarding implementation of Token Bucket Algorithm.
windowStart = "Kitne pichle time tak ke attempts count karo"
"15 minutes pichle tak ke attempts dekho"
"Is time se pehle ke attempts bhool jao"
Rule: "15 minutes mein 5 baar se zyada password change nahi kar sakte"
Config: Capacity=5, RefillRate=0.333 tokens/min
WindowStart nikalne ka formula:
WindowStart = Current Time - (Capacity / RefillRate)
            = Abhi ka time - (5 / 0.333)
            = Abhi ka time - 15 minutes
