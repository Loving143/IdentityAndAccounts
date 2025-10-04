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

# Circuit Breaker
Circuit States Flow: CLOSED (normal) ↓ (failure rate > 50% in last 10 calls) OPEN (block all calls for 10 seconds) ↓ (after 10 seconds wait-duration) HALF_OPEN (allow 1 test call) ↓ (if test call succeeds → CLOSED, if fails → OPEN again)

wait-duration-in-open-state: 10s Meaning: How long the circuit stays OPEN (no requests allowed) before trying again
Behavior: After 10 seconds in OPEN state, circuit moves to HALF_OPEN state to test if service recovered

Purpose: Gives the failing service time to recover

failure-rate-threshold: 50 Meaning: The percentage of failed calls that will trigger the circuit to open
Behavior: When 50% of calls in the sliding window fail, circuit trips to OPEN state

Example: If 5 out of 10 calls fail (50%), circuit opens

sliding-window-size: 10 Meaning: The number of recent calls used to calculate the failure rate
Behavior: Only considers the last 10 calls when calculating the failure percentage

Purpose: Uses recent history, not all-time history
