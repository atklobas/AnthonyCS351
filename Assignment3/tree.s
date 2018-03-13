.data
star:
	.asciz "*"
space:
	.asciz " "
empty:
	.asciz ""
endl:
	.asciz "%s\n"
.text
.global main

printline:
	/*store everything*/
	str lr, [sp, #-4]!
	str r0, [sp, #-4]!
	str r1, [sp, #-4]!
	str r2, [sp, #-4]!
	str r3, [sp, #-4]!

	/*print left spaces of tree*/
	ldr r0, pspace
	ldr r3, =printf
	bl iterate

	/*print left star*/
	ldr r0,[sp, #12]
	bl printf
	ldr r0, pspace
	ldr r2, [sp,#4]
	ldr r1, [sp,#8]
	sub r1,r2,r1
	mov r1,r1,lsl #1
	mov r2,r2,lsl #1
	sub r2,r2,#1
	ldr r3, =printf
	bl iterate

	/*print right star if needed*/
	ldr r0, pendl
	ldr r2, [sp,#8]
	ldr r1,pempty
	cmp r2,#0
	beq printend
	ldr r1,[sp, #12]
	printend:
	bl printf

	/*print bottom row of stars if it's at end*/
	ldr r2, [sp,#4]
	ldr r1, [sp,#8]
	add r1,#1
	cmp r1,r2
	bne notend
	ldr r3, =printf
	ldr r0, pstar
	mov r1, #0
	mov r2, r2, lsl #1
	bl iterate
	ldr r0, pstar
	bl printf
notend:
	/*restore everything*/
	ldr r3, [sp],#4
	ldr r2, [sp],#4
	ldr r1, [sp],#4
	ldr r0, [sp],#4
	ldr lr, [sp],#4
	bx lr

iterate:
	cmp r1,r2
	bge else /* inverted logic to make asm easier*/
	str lr, [sp, #-4]!
	str r0, [sp, #-4]!
	str r1, [sp, #-4]!
	str r2, [sp, #-4]!
	str r3, [sp, #-4]!
	blx r3 
	ldr r3, [sp],#4
	ldr r2, [sp],#4
	ldr r1, [sp],#4
	ldr r0, [sp],#4
	ldr lr, [sp],#4
	add r1,#1
	b iterate /*because this is a tail call we don't need to mess with lr (optomized tail call)*/
else:
	bx lr
main:
	str lr, [sp, #-4]!
	ldr r0, pstar
	mov r2 ,#5
	mov r1,#0
	ldr r3, =printline
	bl iterate	
	
end:
	ldr lr, [sp],#4
	bx lr

pstar: .word star
pspace: .word space
pempty: .word empty
pendl: .word endl


.global printf

