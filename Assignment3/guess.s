.data
prompt:
	.asciz "Guess the number:"
low:
	.asciz "Too low. Guess again. "
high:
	.asciz "Too high. Guess again. "
correct:
	.asciz "You guessed correctly in %d tries!"
input:
	.asciz "%d"
guess:
	.word  0
.text
.global main


main:
	str lr, [sp, #-4]!
	mov r0,#0
	bl time
	bl srand
	bl rand
	/*mod 100*/
	mov r3, #100
	sdiv r1,r0,r3
	mul r1,r3,r1/*have to switch order of arguments*/
	sub r1,r0,r1
	mov r2,#1
	str r1, [sp, #-4]!
	str r2, [sp, #-4]!
	/* top of stack=guess count, 2nd item=secret*/
	ldr r0, pprompt
	bl printf
	ldr r0, pinput
	ldr r1, pguess
	bl scanf
loop:
	ldr r1, pguess
	ldr r1, [r1]
	ldr r0, [sp,#4]
	/*compare guess and secret*/
	cmp r1,r0
	beq end /*skip to end if equal*/
	bgt if
/*else*/
	ldr r0,plow
	b endif
if:
	ldr r0,phigh
endif:
	bl printf
	ldr r0, pinput
	ldr r1, pguess
	bl scanf
	/*incriment count*/
	ldr r0, [sp]
	add r0,r0,#1
	str r0, [sp]
	b loop
end:
	/*empty stack*/
	ldr r1, [sp],#4
	ldr r2, [sp],#4
	ldr r0, pcorrect
	bl printf
	mov r0, #0
	ldr lr, [sp],#4
	bx lr

pprompt: .word prompt
plow: .word low
phigh: .word high
pcorrect: .word correct
pinput: .word input
pguess: .word guess

.global printf
.global scanf
.global rand
.global srand
.global time

