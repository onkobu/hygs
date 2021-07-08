grammar DataModel;

// Parser
start: namespaceDef+ EOF;

namespaceDef: 'namespace' NAME BLOCK_OPEN NEWLINE entities BLOCK_CLOSE NEWLINE*;

entity: 'entity' NAME abbr? BLOCK_OPEN NEWLINE fields BLOCK_CLOSE NEWLINE;

entities: entity+;

abbr: 'abbr' NAME;

fields: field+;

type: 'as' PRIMITIVE constraint?;

field: NAME type NEWLINE;

constraint: maxConstraint;

maxConstraint: 'max' NUMBER;

// lexer

fragment LETTER : [A-Za-z];
fragment DIGIT : [0-9];

BLOCK_OPEN: '{' ;
BLOCK_CLOSE: '}' ;
PRIMITIVE: 'string' | 'integer' | 'flag';
NUMBER : DIGIT+ ;
NAME : LETTER+ ;
WS : [ \r\t] + -> skip ;
NEWLINE : [\n] ;
COMMENT
  :  '#' ~( '\r' | '\n' )* NEWLINE?
  -> skip;
