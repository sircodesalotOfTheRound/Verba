package com.verba.language.codegen.opcodes;

import com.verba.language.exceptions.CompilerException;

/**
 * Created by sircodesalot on 14-4-25.
 */

public enum VMOp {
    // VMRegister Operations
    COPY,           //0x10 COPY.rDEST.rSRC                  # Copy VMRegister Values
    DEREF,          //0x11 DEREF.rDEST.rSRC   (field name)      # Follow the rSRC reference. Store value in rDEST
    SELFDEREF,      //0x12 SELFDEREF.rREG     (field name)      # Follow the rREG. Store value in rREG
    LOAD,           //0x13 LOAD.rDEST.rSRC    (field name)      # Dereference rSRC object, and capture field. Store in rDEST
    SELFLOAD,       //0x14 SELFLOAD.rDREG     (field name)      # Dereference rDEST object, and capture field. Store in rREG
    STORE,          //0x15 STORE.rSRC.rVALUE (field name)      # Dereference rSRC object, and capture field. Store rVALUE

    // Math Operations
    ADDc, //0x20 ADDc.rDEST                  (constant)
    SUBc,//0x21 SUBc.rDEST                  (constant)
    MULTc,//0x22 MULTc.rDEST                 (constant)
    DIVc,//0x23 DIVc.rDEST                  (constant)

    ADD,//0x24 ADD.rDEST.rSRC
    SUB,//0x25 SUB.rDEST.rSRC
    MULT,//0x26 MULT.rDEST.rSRC
    DIV,//0x27 DIV.rDEST.rSRC

    //-- Logical Operations
    AND,//0x30 AND
    OR,//0x31 OR
    NOT,//0x32 NOT
    XOR,//0x33 XOR
    SHL,//0x34 SHL
    SHR,//0x35 SHR

    //-- Tests --
    ISNONE,//0x40 ISNONE.rRESULT.rTEST
    ISSOME,//0x41 ISSOME.rRESULT.rTEST
    ISTRUE,//0x42 ISTRUE.rRESULT.rTEST
    ISFALSE,//0x43 ISFALSE.rRESULT.rTEST

    ISA,//0x44 ISA.rRESULT.rTEST
    HASA,//0x45 HASA.rRESULT.rTEST
    DOESA,//0x46 DOESA.rRESULT.rTEST

    EQ,//0x47 EQ
    NEQ,//0x48 NEQ
    GT,//0x49 GT
    GTE,//0x4a GTE
    LT,//0x4b LT
    LTE,//0x4c LTE

    //-- Jumps --
    LABEL,//0x50 LABEL                       (name)
    JMP,//0x51 JMP                         (label)
    JEQ,//0x52 JEQ                         (label)
    JNEQ,//0x53 JNEQ                        (label)
    JGT,//0x54 JGT                         (label)
    JGTE,//0x55 JGTE                        (label)
    JLT,//0x56 JLT                         (label)
    JLTE,//0x57 JLTE                        (label)
    JSOME,//0x58 JSOME                       (label)
    JNONE,//0x59 JNONE                       (label)

    //-- Creating new Objects--
    NONE,               //0x60 NONE.rREG
    UNIT,               //0x61 UNIT.rREG
    TRUE,               //0x62 TRUE.rREG
    FALSE,              //0x63 FALSE.rREG
    BXLOADi,            //0x64 BXLOAD.i{1, 2, 4, 8}        (value)
    BXLOADF,            //0x65 BXLOADf                     (value)
    NEWREG,             //0x66 NEW.rREG                    (constructor)
    INJECT,             //0x67 INJECT.rREG                 (constructor)
    NEWCLOSURE,         //0x68 NEWCLOSURE.rREG             (constructor)
    LOADs,              //0x69 LOADs.rREG                  (string)
    LOADi1,             //0x6a LOADi1.rREG         (value)
    LOADi2,             //0x6b LOADi2.rREG         (value)
    LOADi4,             //0x6c LOADi4.rREG         (value)
    LOADi8,             //0x6d LOADi8.rREG         (value)
    LOADfs,             //0x6e LOADfs.rREG                  (value)
    LOADfd,             //0x6f LOADfd.rREG                  (value)


    NEWARRAYc,//0x6c NEWARRAYc.rDEST             (size, type)
    NEWARRAY,//0x6d NEWARRAY.rDEST.rSIZE        (type)
    NEWTUPLEc,//0x6e NEWTUPLEc.rDEST             (size, type)
    NEWTUPLE,//0x6f NEWTUPLE.rDEST.rSIZE        (type)


    //-- Data Modification --
    SETARRVAL,//0x70 SETARRVAL.rARR.rINDX.rVAL
    SETUPLEVAL,//0x71 SETUPLEVAL.rTUP.rINDX.rVAL

    //-- Calls --
    CALL,//0x80 CALL.rFIRSTPARAM            (method name)
    CALLWRET,//0x81 CALLWRET.rFIRSTPARAM        (method name)     # Store the return value in the same register
    TAILCALL,//0x82 TAILCALL
    CLOSECALL,//0x83 CLOSECALL.rREG
    RET,//0x84 RET
    YIELD,

    //-- Type Operations --
    CAST,//0x90 CAST.rREG                       (type)
    COPYCAST,//0x91 CAST.rDEST.rSRC             (type)
    TYPE,//0x92 TYPE.rREG                                   # Get an object's type. Store in rREG

    //-- Exceptions --
    CATCH,//0xa0 CATCH
    ENDTRY,//0xa1 ENDTRY
    FINALLY,//0xa2 FINALLY

    //-- Virtual Machine Codes --
    NOP,//0xb0 NOP
    BREAK,//0xb1 BREAK

    //-- Meta Opcodes --
    OPTPARAM,//0xc0 OPTPARAM.rDEST.rSOURCE                                # Optional Default parameter
    OPTNEWPARAM,//0xc1 OPTNEWPARAM.rSOURCE                                   # Optional New parameter
    OPTINJPARAM,//0xc1 OPTINJPARAM.rSOURCE                                   # Optional Inject parameter
    ASPECT;//ASPECT.rSOURCE=             0xc2//(name)                     # Aspect

    public int asInt() {
        switch (this) {
            // Math
            case ADD:
                return 0x24;
            case SUB:
                return 0x25;
            case MULT:
                return 0x26;
            case DIV:
                return 0x27;

            // Load
            case LOADs:
                return 0x69;

            case LOADi1:
                return 0x6a;
            case LOADi2:
                return 0x6b;
            case LOADi4:
                return 0x6c;
            case LOADi8:
                return 0x6d;

            // CALL
            case CALL:
                return 0x80;
            case RET:
                return 0x84;
        }

        throw new CompilerException("No such opcode");
    }
}
