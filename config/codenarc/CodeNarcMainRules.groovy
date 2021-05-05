File starterFile = new File("config/codenarc/StarterRuleSet-AllRulesByCategory.groovy.txt")

ruleset {
	description "CodeNarc Main Ruleset"
	ruleset("file:${starterFile.path}") {
		ClassJavadoc(enabled: false)
		UnnecessaryGString(enabled: false)
		UnnecessaryReturnKeyword(enabled: false)
		AbstractClassWithoutAbstractMethod(enabled: false)
		IfStatementBraces(enabled: false)
		Instanceof(enabled: false)
		FactoryMethodName(enabled: false)
		SpaceAroundMapEntryColon(characterAfterColonRegex: /\ /)
		CloneWithoutCloneable(enabled: false)
		JUnitAssertEqualsConstantActualValue(enabled: false)
		UnsafeImplementationAsMap(enabled: false)
		SpaceAroundMapEntryColon(enabled: false)
		SpaceAfterOpeningBrace(enabled: false)
		SpaceBeforeClosingBrace(enabled: false)
		JavaIoPackageAccess(enabled: false)
		UnnecessaryObjectReferences(enabled: false)
		DuplicateMapLiteral(enabled: false)
		DuplicateStringLiteral(enabled: false)
		LineLength(enabled: false)
		VariableName(finalRegex: /[a-zA-Z]+/)
		DuplicateNumberLiteral(enabled: false)
		JUnitPublicProperty(enabled: false)
		JUnitPublicNonTestMethod(enabled: false)
		BuilderMethodWithSideEffects(enabled: false)
		UnnecessarySubstring(enabled: false)
		PackageName(enabled: false)
	}
}
